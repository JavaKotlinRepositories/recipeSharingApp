import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  http: HttpClient;router:Router;cookieService:CookieService;
  constructor(http:HttpClient,router:Router,cookieService:CookieService) { 
    this.http = http;
    this.router = router;
    this.cookieService = cookieService;
  }
  login(email: string, password: string){
    console.log('login', email, password)
    this.http.post<any>("http://localhost:8080/login", { chefEmail: email, chefPassword: password}).subscribe((data)=>{
      console.log(data);
      if(data.email && data.token && data.email!=null && data.token!=null){
        this.cookieService.set('AuthCookie',JSON.stringify(data),1,'/','',true,'Strict');
        console.log(JSON.parse(this.cookieService.get('AuthCookie')))
      }
    })
    
    this.router.navigate(['/home']);
  }
}
