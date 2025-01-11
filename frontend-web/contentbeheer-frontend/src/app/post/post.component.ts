import {Component, inject, OnInit} from '@angular/core';
import {PostService} from "../services/post/post.service";
import {AuthService} from "../services/auth/auth.service";
import {Post} from "../models/post.model";
import {MatCardModule} from "@angular/material/card";
import {DatePipe} from "@angular/common"; // DatePipe is om de datum te formateren
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {Router, RouterLink} from "@angular/router";
import {FilterComponent} from "./filter/filter.component";
import {Filter} from "../models/filter.model";

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    FilterComponent
  ],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent implements OnInit{
  posts: Post[] = [];
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);
  router = inject(Router);

  filteredData: Post[] = [];

  constructor() {}

  ngOnInit() {
    this.getPosts();
  }

  getPosts(): void {
    this.postService.getPublishedPosts().subscribe(posts => {
      this.posts = posts;
      this.filteredData = this.posts;
    });
  }

  navigateToPost(id: number | undefined): void {
    this.router.navigate(['posts', id]);
  }

  handleFilter(filter: Filter) {
    this.filteredData = this.posts.filter(post => this.isPostMatchingFilter(post, filter));
  }

  isPostMatchingFilter(post: Post, filter: Filter): boolean {
    const matchesAuthor = post.author.toLowerCase().includes(filter.author.toLowerCase());
    const matchesContent = post.content.toLowerCase().includes(filter.content.toLowerCase());
    const matchesDate = filter.date ? (post.creationDate ? new Date(post.creationDate).toDateString() === filter.date.toDateString() : false) : true;

    return matchesAuthor && matchesContent && matchesDate;
  }
}
