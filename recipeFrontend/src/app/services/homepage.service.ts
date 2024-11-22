import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { loginInfoType, LoginService } from './login.service';
import { BehaviorSubject } from 'rxjs';

export type userProfileInfo= {
  chefEmail:string,
  chefName: string,
  chefProfileImage: string
}
@Injectable({
  providedIn: 'root'
})
export class HomepageService {
  
  http: HttpClient;loginServ:LoginService;loginInfo:loginInfoType;
  private userProfileInfo=new BehaviorSubject<userProfileInfo>({
    chefEmail: "",
    chefName: "",
    chefProfileImage: ""});
  public _userProfileInfo=this.userProfileInfo.asObservable();
  constructor(http:HttpClient,loginServ:LoginService) {
    this.http = http;
    this.loginServ = loginServ;
    this.loginInfo={email: '',token: ''}
    this.loginServ._loginInfo.subscribe({
      next:(data)=>{
      this.loginInfo=data;
    },error:(error)=>{
      this.loginInfo={email: '',token: ''}
    },
  })
  }
  getUserProfileInfo(){
    if(this.loginInfo?.email=='' || this.loginInfo?.token==''){
      this.userProfileInfo.next({
        chefEmail: "",
        chefName: "",
        chefProfileImage: ""})     
        return ;
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.loginInfo?.token}` // Attach the token here
    });
    this.http.get<userProfileInfo>('http://localhost:8080/userprofileinfo',{headers}).subscribe({next:data=>{
      console.log('User profile info:', data);
      this.userProfileInfo.next(data);
      return;
    },error:err=>{
      
    }})
    this.userProfileInfo.next({
      chefEmail: "",
      chefName: "",
      chefProfileImage: ""})
      return;
  }
  
}
