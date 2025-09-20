import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'e-commerce-frontend';
  showHeader = true;

  constructor(private router: Router) {
    // Watch for route changes
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const url = event.urlAfterRedirects;
        console.log('Current URL:', url);
        const username = localStorage.getItem('username');
    
        // Hide header for login/signup pages if not logged in
        // if ((url.startsWith('/login') || url.startsWith('/signup')) && !username) {
        //   this.showHeader = false;
        // } else {
        //   this.showHeader = true;
        // }
      }
    });
    console.log('showHeader:', this.showHeader);    
  }

}