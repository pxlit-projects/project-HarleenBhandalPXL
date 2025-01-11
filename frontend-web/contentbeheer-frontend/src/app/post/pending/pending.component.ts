import {Component, inject} from '@angular/core';
import {DatePipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {Post} from "../../models/post.model";
import {PostService} from "../../services/post/post.service";
import {RouterLink} from "@angular/router";
import {ReviewService} from "../../services/post/review.service";

@Component({
  selector: 'app-pending',
  standalone: true,
  imports: [
    DatePipe,
    MatIcon,
    MatIconButton,
    RouterLink
  ],
  templateUrl: './pending.component.html',
  styleUrl: './pending.component.css'
})
export class PendingComponent {
  pendingPosts: Post[] = [];
  postService: PostService = inject(PostService);
  reviewService: ReviewService = inject(ReviewService);

  constructor() {}

  ngOnInit() {
    this.getPendingPosts();
  }

  getPendingPosts(): void {
    this.postService.getPendingPosts().subscribe(posts => {
      this.pendingPosts = posts;
    });
  }

  approvePost(id: number | undefined): void {
    this.reviewService.approvePost(id).subscribe(() => {
      this.getPendingPosts();
    });
  }
}
