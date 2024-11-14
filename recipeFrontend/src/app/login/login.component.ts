import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
  email="";password="";loginServ:LoginService;
  constructor(loginServ:LoginService){
    this.loginServ = loginServ;
  }
  login(){
    if(this.email.length > 0 || this.password.length > 0){
      this.loginServ.login(this.email, this.password);
    }
  }
}
