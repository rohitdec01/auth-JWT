import * as azure from "@pulumi/azure";
import * as docker from "@pulumi/docker";
import * as pulumi from "@pulumi/pulumi";

// Create an Azure Resource Group
const resourceGroup = new azure.core.ResourceGroup("auth-jwt-group");

const appServicePlan = new azure.appservice.Plan("appservice-plan", {
    kind: "Linux",
    resourceGroupName: resourceGroup.name,
    reserved: true,
    sku: {
        tier: "Basic",
        size: "B1",
    },
});

const registry = new azure.containerservice.Registry("myacr", {
    resourceGroupName: resourceGroup.name,
    sku: "Basic",
    adminEnabled: true,
});

const customImage = "auth-jwt-app";

const myImage = new docker.Image(customImage, {
    imageName: pulumi.interpolate`${registry.loginServer}/${customImage}:v1.0.0`,
    build: {
        context: `../`,
    },
    registry: {
        server: registry.loginServer,
        username: registry.adminUsername,
        password: registry.adminPassword,
    },
});

const appService = new azure.appservice.AppService(customImage, {
    resourceGroupName: resourceGroup.name,
    appServicePlanId: appServicePlan.id,
    appSettings: {
        WEBSITES_ENABLE_APP_SERVICE_STORAGE: "false",
        DOCKER_REGISTRY_SERVER_URL: pulumi.interpolate`https://${registry.loginServer}`,
        DOCKER_REGISTRY_SERVER_USERNAME: registry.adminUsername,
        DOCKER_REGISTRY_SERVER_PASSWORD: registry.adminPassword,
        // Our custom image exposes port 9000.
        WEBSITES_PORT: "9000",
    },
    siteConfig: {
        alwaysOn: true,
        linuxFxVersion: pulumi.interpolate`DOCKER|${myImage.imageName}`,
    },
    httpsOnly: true,
});


// Create an Azure resource (Storage Account)
/*const account = new azure.storage.Account("storage", {
    // The location for the storage account will be derived automatically from the resource group.
    resourceGroupName: resourceGroup.name,
    accountTier: "Standard",
    accountReplicationType: "LRS",
});*/

// Export the connection string for the storage account
// export const connectionString = account.primaryConnectionString;

export const appServiceEndpoint = pulumi.interpolate`https://${appService.defaultSiteHostname}`;
