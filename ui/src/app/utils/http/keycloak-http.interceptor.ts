import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from '../keycloak/keycloak.service';

export const keycloakHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeycloakService); // Inject the KeycloakService
  const token = keycloakService.keycloak?.token; // Get the token from KeycloakService
  if (token) {
    // Check if the token is available
    const authReq = req.clone({
      // Clone the request and add the Authorization header
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`, // Set the Authorization header with the token
      }),
    });
    return next(authReq); // Return the modified request with the Authorization header
  }

  return next(req); // If no token is available, return the original request
};
