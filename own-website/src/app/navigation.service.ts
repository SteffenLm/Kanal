import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  private HEADERHEIGHT = 56;
  public openSidenav = new Subject();
  public height: number;
  public openSidenav$ = this.openSidenav.asObservable();

  constructor() {
    this.height = window.innerHeight - this.HEADERHEIGHT;
   }
}
