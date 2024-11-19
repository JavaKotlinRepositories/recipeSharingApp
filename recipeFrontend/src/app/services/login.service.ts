import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject } from 'rxjs';

export type loginInfoType={
  email: string,
  token: string
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  
  http: HttpClient;router:Router;cookieService:CookieService;
  private loginInfo=new BehaviorSubject<loginInfoType>({email: '',token: ''});
  public _loginInfo=this.loginInfo.asObservable();
  constructor(http:HttpClient,router:Router,cookieService:CookieService) { 
    this.http = http;
    this.router = router;
    this.cookieService = cookieService;
    this.checkLogin();
  }
  login(email: string, password: string){
    console.log('login', email, password)
    this.http.post<any>("http://localhost:8080/login", { chefEmail: email, chefPassword: password}).subscribe((data)=>{
      console.log(data);
      if(data.email && data.token && data.email!=null && data.token!=null){
        this.updateLoginInfo({email: data.email, token: data.token});
      }
    })
    this.router.navigate(['/home']);
  }

  checkLogin(){
    if(this.cookieService.check('AuthCookie'))
    {
      try{
        this.updateLoginInfo(JSON.parse(this.cookieService.get('AuthCookie')))
      }
      catch(err){
        this.clearLoginDetails();
        this.router.navigate(['/login']);
        return;
      }
    }
    
  }
  updateLoginInfo(loginInfo: loginInfoType){
    this.cookieService.set('AuthCookie',JSON.stringify(loginInfo),1,'/','',true,'Strict');
    this.loginInfo.next(loginInfo)
  }
  clearLoginDetails(): void {
    this.cookieService.delete('AuthCookie');
    this.loginInfo.next({email:'',token:''});
  }
}
