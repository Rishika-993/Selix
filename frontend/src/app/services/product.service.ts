import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  getAll(params: any) {
    return this.http.get(this.baseUrl, { params });
  }

  create(product: any, categoryId: number) {
    return this.http.post(`${this.baseUrl}/categories/${categoryId}`, product);
  }

  delete(productId: number) {
    return this.http.delete(`${this.baseUrl}/${productId}`);
  }

  // search(term: string, params: any) {
  //   return this.http.get(`${this.baseUrl}/search/${term}`, { params });
  // }

  update(productId: number, product: any) {
    return this.http.put(`${this.baseUrl}/${productId}`, product);
  }

  search(keyword: string, params: any) {
    return this.http.get(`${this.baseUrl}/search/${keyword}`, { params });
  }

  getAllCategories(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/categories/all');
  }
  
  getProductsByCategory(categoryId: number, params: any): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/products/categories/${categoryId}`, { params });
  }
  
  createProduct(categoryId: number, data: any): Observable<any> {
    return this.http.post(`http://localhost:8080/api/products/categories/${categoryId}`, data);
  }

  getMyProducts(params: any): Observable<any[]> {
    return this.http.get<any>(`http://localhost:8080/api/products`, { params });
    // return this.http.post<any[]>('http://localhost:8080/api/products', param, { observe: 'body' });
  }
  
  deleteProduct(productId: number) {
    return this.http.delete(`${this.baseUrl}/products/${productId}`);
  }  
  
  
}
