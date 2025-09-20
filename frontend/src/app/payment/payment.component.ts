import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
})
export class PaymentComponent {
  paymentMethod: string = '';
  totalAmount: number = 0;
  orderData: any = {};

  constructor(private router: Router) {}

  ngOnInit() {
    // get payment data passed while routing
    const state = history.state;
    this.paymentMethod = state.paymentMethod;
    this.totalAmount = state.totalAmount;
    this.orderData = state.orderData;
  }

  confirmPayment(status: 'SUCCESS' | 'FAILED') {
    if (status === 'SUCCESS') {
      // navigate to final order placement
      this.router.navigate(['/order-place-success'], { state: { ...this.orderData, pgStatus: 'SUCCESS', pgResponseMessage: 'Payment Successful' } });
    } else {
      alert('Payment Failed. Please try again.');
      this.router.navigate(['/cart']);
    }
  }
}
