import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-place-success',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-place-success.component.html',
  styleUrl: './order-place-success.component.css'
})
export class OrderPlaceSuccessComponent {

  paymentMethod = 'ONLINE'; // default

  constructor(private router: Router, private orderService: OrderService) {}

  ngOnInit() {
    const orderData = history.state;

    if (orderData) {
      this.orderService.placeOrder(this.paymentMethod, orderData).subscribe({
        next: (res) => {
          setTimeout(() => {
            this.router.navigate(['/my-orders']);
          }, 2000); // 2 seconds delay
          // After placing order
          // this.router.navigate(['/my-orders']).then(() => {
          //   setTimeout(() => {
          //     window.location.reload(); // Only once after navigating
          //   }, 1000000);
          // });
        },
        error: (err) => {
          alert('Order failed after payment: ' + (err.error?.message || 'Unknown error'));
          this.router.navigate(['/']);
        }
      });
    } else {
      this.router.navigate(['/']);
    }
  }
}
