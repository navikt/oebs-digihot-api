# oebs-digihot-api mot DIGIHOT
REST API'er tilbudt av Oebs.

## Running Locally

To run the service locally, use the `local` profile and set the following environment variables. Values for all secrets can be retrieved from the NAIS console for the application `oebs-digihot-api-t1`:

- `DB_USERNAME` – username for OEBS
- `DB_PASSWORD` – password for OEBS
- `DB_URL` – URL for OEBS, find in https://confluence.adeo.no/spaces/ITO/pages/39159672/OeBS+Oversikt+over+milj%C3%B8er
- `AZURE_APP_WELL_KNOWN_URL` – discovery URL for the Azure AD app

You must also have connectivity to the OEBS database in the secure zone.
You can either use **vdi-utvikler-oebs** (a VDI set up for development in the secure zone) or the **Global Secure Access Client**.
For more information, see the [oksty developer documentation](https://github.com/navikt/oksty-documentation).

[Swagger UI](http://localhost:8080/swagger-ui/index.html) is available when running locally,
but all endpoints are protected by Azure AD by default. To test endpoints without authentication,
replace the `@Protected` annotation in a controller with `@Unprotected` for one of the GET endpoints.
