import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root',
})
export class KeycloakService {
  private _keycloak: Keycloak | undefined;

  constructor() {}

  // Singeton instance of Keycloak
  get keycloak(): Keycloak {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8080',
        realm: 'real-time-chat-fullstack',
        clientId: 'real-time-chat-app',
      });
    }
    return this._keycloak;
  }

  async init(): Promise<void> {
    const authenticated = await this.keycloak.init({
      onLoad: 'login-required',
    });
  }

  async login(): Promise<void> {
    await this.keycloak?.login();
  }

  get userId(): string | undefined {
    return this.keycloak?.tokenParsed?.sub;
  }

  get isTokenValid(): boolean {
    return !this.keycloak.isTokenExpired();
  }

  get fullName(): string | undefined {
    return this.keycloak?.tokenParsed?.['name'];
  }

  logout(): Promise<void> {
    return this.keycloak?.login({
      redirectUri: 'http://localhost:4200',
    });
  }

  accoutnManagement(): Promise<void> {
    return this.keycloak?.accountManagement();
  }
}
