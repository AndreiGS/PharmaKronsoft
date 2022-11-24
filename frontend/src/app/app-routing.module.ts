import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { AuthGuard, NegateAuthGuard } from './shared/guards/auth-guard';

const routes: Routes = [
  { path: '', component: HomePageComponent, pathMatch: 'prefix', canActivate: [AuthGuard] }
  , { path: 'login', component: LoginPageComponent, pathMatch: 'prefix', canActivate: [NegateAuthGuard] }
  , { path: 'register', component: RegisterPageComponent, pathMatch: 'prefix', canActivate: [NegateAuthGuard] }
];

@NgModule({ 
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
