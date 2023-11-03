# LocationProducer
https://techcommunity.microsoft.com/t5/educator-developer-blog/how-to-login-to-azure-with-github-actions/ba-p/3573050

az ad sp create-for-rbac --name "CICD" --role contributor --scopes /subscriptions/3bcb201f-aa6d-4cdb-9e58-25fcdff2dc3a --sdk-auth

{
  "clientId": "680e6ea6-276e-4ae9-afb9-7b18309e2253",
  "clientSecret": "pET8Q~ZktZaHUcuM6Xv1C8HpxFdYswR1-_Nzdb1_",
  "subscriptionId": "3bcb201f-aa6d-4cdb-9e58-25fcdff2dc3a",
  "tenantId": "b030234f-2018-44d4-988f-171292ddcc6e",
  "activeDirectoryEndpointUrl": "https://login.microsoftonline.com",
  "resourceManagerEndpointUrl": "https://management.azure.com/",
  "activeDirectoryGraphResourceId": "https://graph.windows.net/",
  "sqlManagementEndpointUrl": "https://management.core.windows.net:8443/",
  "galleryEndpointUrl": "https://gallery.azure.com/",
  "managementEndpointUrl": "https://management.core.windows.net/"
}
