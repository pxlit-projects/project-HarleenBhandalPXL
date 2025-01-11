import {Component, inject} from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {MatInput} from "@angular/material/input";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    MatInput
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  authService: AuthService = inject(AuthService);

  constructor() {
  }

  toggleRole(): void {
    this.authService.toggleRole();
  }
}
