import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RundaufEckigComponent } from '../app/sidenav/rundaufeckig/rundaufeckig.component';

const routes: Routes = [
  { path: 'rund-auf-eckig', component: RundaufEckigComponent },
  { path: '**', component: RundaufEckigComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
