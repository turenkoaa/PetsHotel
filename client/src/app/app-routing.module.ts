import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserCabinetComponent} from './user-cabinet/user-cabinet.component';
import {RouterModule, Routes} from '@angular/router';
import {UserInfoComponent} from './user-cabinet/user-info/user-info.component';
import {LoginComponent} from './login/login.component';
import {AdminComponent} from './user-cabinet/admin/admin.component';

const routes: Routes = [
  { path: 'userCabinet/:id', component: UserCabinetComponent },
  { path: 'user-info', component: UserInfoComponent },
  { path: 'login', component: LoginComponent},
  { path: 'admin', component: AdminComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
