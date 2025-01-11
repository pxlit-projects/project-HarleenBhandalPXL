import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ActivatedRoute} from '@angular/router';
import {ReviewService} from "../../services/post/review.service";
import {PostService} from "../../services/post/post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reject',
  standalone: true,
  imports: [FormsModule, MatButton, MatFormField, MatInput, MatLabel],
  templateUrl: './reject.component.html',
  styleUrl: './reject.component.css'
})
export class RejectComponent {
  reason: string = '';
  author: string = '';
  postId: number = -1;
  errorMessage: string = '';

  reviewService: ReviewService = inject(ReviewService);
  postService: PostService = inject(PostService);
  route: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);

  constructor() {
    this.postId = this.route.snapshot.params['id'];
  }

  onSubmit(form: any) {
    this.author = form.value.author;
    this.reason = form.value.reason;

    if (form.valid) {
      this.reviewService.rejectPost(this.postId, this.reason, this.author).subscribe(() => {
        this.errorMessage = '';
        this.postService.getPendingPosts();
        this.router.navigate(['/pending']);
      });
    } else {
      this.errorMessage = 'Form is invalid. Please fill out all required fields.';
    }
  }
}
