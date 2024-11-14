import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  router: any;
  constructor(router:Router) { 
    this.router=router;
  }

login(){
this.router.navigate(['/login'])
}
signup(){
  this.router.navigate(['/signup'])
}

home(){
  this.router.navigate(['/home'])
}
}
