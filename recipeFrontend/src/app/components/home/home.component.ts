import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { loginInfoType, LoginService } from '../../services/login.service';
import { LoggedinhomeComponent } from "../loggedinhome/loggedinhome.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [LoggedinhomeComponent,CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
router:Router;loginService:LoginService;loginInfo:loginInfoType={email:'',token:''};

constructor(router:Router,loginService:LoginService){
this.loginService = loginService;
this.router = router;

this.loginService._loginInfo.subscribe(loginInfo => {
  this.loginInfo = loginInfo;
});
}
login(){
  this.router.navigate(['/login']);
}
signup(){
  this.router.navigate(['/signup']);
}
}
