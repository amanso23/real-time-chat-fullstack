import {
  ApplicationConfig,
  inject,
  provideAppInitializer,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { KeycloakService } from './utils/keycloak/keycloak.service';
import { keycloakHttpInterceptor } from './utils/http/keycloak-http.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([keycloakHttpInterceptor])), // Add the created interceptor to the HTTP client
    provideAppInitializer(() => {
      // This function will be called when the app is initialized
      const initFn = ((key: KeycloakService) => {
        // This function initializes the Keycloak service
        return () => key.init();
      })(inject(KeycloakService));
      return initFn(); // Call the init function to initialize Keycloak
    }),
  ],
};
