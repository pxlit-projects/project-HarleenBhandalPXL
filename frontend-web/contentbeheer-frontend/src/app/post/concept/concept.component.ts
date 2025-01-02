import {Component, inject} from '@angular/core';
import {DatePipe} from "@angular/common";
import {Post} from "../../models/post.model";
import {PostService} from "../../services/post/post.service";
import {AuthService} from "../../services/auth/auth.service";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-concept',
  standalone: true,
  imports: [
    DatePipe,
    MatIcon,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './concept.component.html',
  styleUrl: './concept.component.css'
})
export class ConceptComponent {
  conceptPosts: Post[] = [];
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);

  constructor() {}

  ngOnInit() {
    this.getConceptPosts();
  }

  getConceptPosts(): void {
    this.postService.getConceptPosts().subscribe(posts => {
      this.conceptPosts = posts;
    });
  }
}
