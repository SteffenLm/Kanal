import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

import { MatSidenav } from '@angular/material/sidenav';

import { NavigationService } from '../navigation.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  @ViewChild('drawer') private drawer: MatSidenav;

  constructor(private navigationService: NavigationService) { }

  public ngOnInit(): void {
    this.navigationService.openSidenav$.subscribe(
      () => { this.drawer.toggle(); }
    );
  }

  public triggerFooter() {
    this.navigationService.openSidenav.next();
  }

}
