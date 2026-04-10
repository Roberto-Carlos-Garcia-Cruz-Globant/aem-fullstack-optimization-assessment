# DECISIONS.md

This document records the architectural decisions and technical improvements made during the AEM Assessment project.

## 1. Project Initialization and Build Process
**Context:** The initial project had installation errors and missing metadata in the JCR Vault definitions.
**Decision:**
* Corrected the `filevault-package-maven-plugin` properties across modules to ensure proper JCR installation.
* Updated the Parent POM and module-level POMs to resolve dependency conflicts.
* **Streamlined Workflow:** Implemented a unified build command for efficiency:
  `mvn install -PautoInstallBundle -PautoInstallPackage -DskipTests`

## 2. Template Structure and Component Reusability
**Context:** The project lacked a standard editable template structure, making component governance difficult.
**Decision:**
* Refactored templates to follow the standard AEM Editable Templates pattern, including robust `initial` and `structure` nodes.
* Enabled Policy management within the Layout Container.
* **Efficiency:** Prioritized the use of AEM Core Components. Custom development was strictly limited to the required `weather` component (`/apps/assessment/components/weather`) to minimize technical debt.

## 3. Backend Logic and Multi-Tenancy (OSGi Factory)
**Context:** The initial requirement involved inline Front-End code, which is difficult to maintain and insecure.
**Decision:**
* Moved all business logic to the Backend (Sling Models and OSGi Services).
* **Scalability:** Implemented an **OSGi Factory Configuration** (`WeatherTenantConfig.java`) to support Multi-Tenancy. This allows multiple sites/clients to reuse the same service with unique API keys and endpoints.
* **Architecture:** Decentralized the logic into three specialized layers:
    * `WeatherTenantConfigProviderImpl`: Manages the registry of active configurations.
    * `WeatherTenantConfigServiceImpl`: Handles individual tenant data.
    * `WeatherServiceImpl`: Executes the API orchestration.
* **Tenant Resolution:** The service automatically detects the correct configuration by comparing the `siteName` property with the current page path.
* **Code Quality:** Introduced a dedicated **Constants class** to manage reusable strings (e.g., path separators, property names, and default values). This prevents "magic strings," reduces errors, and centralizes configuration keys.

## 4. Dispatcher Security (Hardening)
**Context:** The default Dispatcher filters were set to "Allow All," posing a significant security risk.
**Decision:**
* Implemented a **"Deny by Default"** strategy in `filters.any`.
* Restricted access to only essential paths: Project-specific content paths, Assets (DAM), Clientlibs, and the CSRF token endpoint. This prevents exposure of sensitive JCR structures and internal `/bin` servlets.

---

## Pending Improvements & Known Limitations

### 1. Automated Testing
**Status:** Not implemented due to time constraints.
**Next Steps:**
* Complete the test folder structure and add `AemContext` (wcm-io) and `Mockito` dependencies to the Core POM.
* Develop Unit Tests for `WeatherModel` and `WeatherServiceImpl` using `AEMExtension` to ensure logic coverage.

### 2. Secure Secret Management (API Key)
**Status:** API Keys are currently stored as configuration strings.
**Recommendation for Production:**
* **AEM Cloud Service:** Use Environment Variables/Secrets via Cloud Manager.
* **AEM 6.5 (On-Prem):** Use AEM’s `CryptoSupport` service to store encrypted API Keys, ensuring that sensitive credentials are never stored in plain text within the JCR or the OSGi console.