import { CanActivateFn } from '@angular/router';

export const roleGuard: CanActivateFn = () => {
    const stored = localStorage.getItem('roles');
    const roles: string[] = stored ? JSON.parse(stored) : [];
  
    return roles.includes('ROLE_ADMIN') || roles.includes('ROLE_SELLER');
  };
  