import { Routes } from '@angular/router';
import {CreatePostComponent} from "./post/create/create-post/create-post.component";
import {PostComponent} from "./post/post.component";

export const routes: Routes = [
  {
    path: 'posts',
    component: PostComponent
  },
  {
    path: 'create-post',
    component: CreatePostComponent
  },
  {
    path: '',
    redirectTo: 'posts',
    pathMatch: 'full'
  },
];
