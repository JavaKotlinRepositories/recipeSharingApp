import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
  email="";password="";loginServ:LoginService;router:Router;
  constructor(loginServ:LoginService,router:Router){
    this.loginServ = loginServ;
    this.router = router;
    this.loginServ._loginInfo.subscribe(data=>{
      console.log('Login info updated', data);
      if(data.email !== ""){
        this.router.navigate(['/']);
      }
    })
  }
  login(){
    if(this.email.length > 0 || this.password.length > 0){
      this.loginServ.login(this.email, this.password);
    }
  }
}
