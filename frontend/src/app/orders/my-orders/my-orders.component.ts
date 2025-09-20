import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-my-orders',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  orders: any[] = [];
  loading = true;
  error = '';

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.getMyOrders();
  }

  getMyOrders() {
    this.orderService.getMyOrders().subscribe({
      next: (res: any[]) => {
        console.log('Orders fetched:', JSON.stringify(res));
        this.orders = res;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load orders.';
        this.loading = false;
        console.error(err);
      }
    });
  }
 
}
