import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient) {}

  // placeOrder(data: any) {
  //   return this.http.post(`${this.baseUrl}`, data);
  // }

  placeOrder(paymentMode: string, orderPayload: any): Observable<any> {
    return this.http.post(
      `http://localhost:8080/api/orders/users/payments/online`,
      orderPayload
    );
  }
  
  
  // getAddressesByUserId(userId: string) {
  //   return this.http.get(`http://localhost:8080/api/addresses/${userId}`);
  // }

  getAddressesByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/addresses/${userId}`);
  }
  

  getCurrentUser() {
    return this.http.get('http://localhost:8080/api/auth/current-user');
  }
  
  getCurrentUserAddresses(pageNumber: number, pageSize: number): Observable<any> {
    return this.http.get(
      `http://localhost:8080/api/addresses/users/current?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addressId&sortOrder=des`
    );
  }  

  getMyOrders(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/orders');
  }
  
  saveAddress(address: any) {
    return this.http.post(`http://localhost:8080/api/addresses`, address);
  }  
  
}
