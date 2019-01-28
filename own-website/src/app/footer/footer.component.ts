import { Component, OnInit } from '@angular/core';
import { NavigationService } from '../navigation.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  public showFooter: boolean;

  constructor(private navigationService: NavigationService) { }

  public ngOnInit(): void {
    this.showFooter = false;
    this.navigationService.openSidenav$.subscribe(
      () => {
        this.showFooter = !this.showFooter;
      }
    );
  }

}
