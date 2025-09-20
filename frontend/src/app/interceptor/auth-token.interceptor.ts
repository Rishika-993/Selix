import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

// export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {
//   const token = localStorage.getItem('token'); 
//     if (token) {
//       req = req.clone({
//         setHeaders: {
//           Authorization: `Bearer ${token}`
//         }
//       });
//     }
//   return next(req);
// };

export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {

  const router = inject(Router); // ✅ inject instead of constructor
  
  const token = localStorage.getItem('token'); 
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.status === 401) {
        // Token expired or invalid
        console.warn('🔐 Unauthorized - logging out user...');

        // Clear user data
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('roles');
        localStorage.removeItem('pageSize');

        // Optionally show toast or alert
        // alert('Session expired. Please login again.');

        // Redirect to login
        router.navigate(['/login']);

        // Reload to reset signals if needed
        // window.location.reload();
      }

      return throwError(() => err);
    })
  )
};