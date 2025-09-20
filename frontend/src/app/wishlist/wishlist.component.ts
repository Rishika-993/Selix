import { Component, OnInit } from '@angular/core';
import { WishlistService } from '../services/wishlist.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent implements OnInit {
  wishlistItems: any[] = [];
  userId: any = null;
  loading = true;

  constructor(private wishlistService: WishlistService) {}

  ngOnInit() {
    this.loadWishlist();
  }

  loadWishlist() {
    this.userId = localStorage.getItem('userId');
    this.wishlistService.getWishlist(this.userId).subscribe(data => {
      this.wishlistItems = data;
      this.loading = false;
      console.log('Wishlist items:', this.wishlistItems);
    });
  }

  removeFromWishlist(productId: number) {
    this.userId = localStorage.getItem('userId');
    console.log('Removing product with ID:', productId);
    this.wishlistService.removeProductFromWishlist(this.userId, productId).subscribe(() => {
      this.loadWishlist();
    });
  }
}

