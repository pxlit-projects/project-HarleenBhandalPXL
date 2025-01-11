import {Component, inject} from '@angular/core';
import {DatePipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {PostService} from "../../../services/post/post.service";
import {Post} from "../../../models/post.model";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-rejected',
  standalone: true,
  imports: [
    DatePipe,
    MatIcon,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './rejected.component.html',
  styleUrl: './rejected.component.css'
})
export class RejectedComponent {
  rejectedPosts: Post[] = [];

  postService: PostService = inject(PostService);

  constructor() {}

  ngOnInit() {
    this.getRejectedPosts();
  }

  getRejectedPosts(): void {
    this.postService.getRejectedPosts().subscribe(posts => {
      this.rejectedPosts = posts;
    });
  }

  updateToPending(id: number | undefined): void {
    this.postService.updateToPending(id).subscribe(() => {
      this.getRejectedPosts();
    });
  }
}
