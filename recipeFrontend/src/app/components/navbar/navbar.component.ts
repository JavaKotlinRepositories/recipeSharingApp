import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { loginInfoType, LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  router: Router;loginService:LoginService;loginInfo:loginInfoType ={email: '', token: ''};
  constructor(router:Router,loginService:LoginService) { 
    this.router=router;
    this.loginService=loginService;
    this.loginService._loginInfo.subscribe((data)=>{
      this.loginInfo = data;
    })
  }

login(){
this.router.navigate(['/login'])
}
signup(){
  this.router.navigate(['/signup'])
}
logout(){
  this.loginService.clearLoginDetails();
  this.router.navigate(['/home'])
}
home(){
  this.router.navigate(['/home'])
}
}
