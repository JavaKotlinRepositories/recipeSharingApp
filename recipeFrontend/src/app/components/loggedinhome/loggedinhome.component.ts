import { Component } from '@angular/core';
import { HomepageService, userProfileInfo } from '../../services/homepage.service';

@Component({
  selector: 'app-loggedinhome',
  standalone: true,
  imports: [],
  templateUrl: './loggedinhome.component.html',
  styleUrl: './loggedinhome.component.css'
})
export class LoggedinhomeComponent {
  homeServe:HomepageService;userProfileInfo={
    chefEmail: "",
    chefName: "",
    chefProfileImage: ""};
  
  constructor(homeServe:HomepageService){
    this.homeServe = homeServe;
    this.homeServe.getUserProfileInfo()
    this.homeServe._userProfileInfo.subscribe({
      next: (data) => {
        this.userProfileInfo = data;
      }
    })

  }

}
