import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-create',
  standalone: true,
  templateUrl: './product-create.component.html',
  imports: [CommonModule, FormsModule],
})
export class ProductCreateComponent {
  private productService = inject(ProductService);
  private router = inject(Router);

  product: any = {
    productName: '',
    price: null,
    specialPrice: null,
    description: '',
    productImage: '',
    categoryId: '',
    discount: null,
  };

  categories: any[] = [];

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe({
      next: (res: any[]) => this.categories = res,
      error: (err) => {
        console.error('Failed to load categories:', err);
        alert('Failed to load categories');
      }
    });
  }

  createProduct(): void {
    if (!this.product.categoryId) {
      alert("Please select a category.");
      return;
    }

    this.productService.createProduct(this.product.categoryId, this.product).subscribe({
      next: () => {
        alert('✅ Product created!');
        this.router.navigate(['/products']);
      },
      error: (err) => {
        const msg = err.error?.message || 'Unknown error';
        alert('❌ Failed: ' + msg);
      }
    });
  }
}
