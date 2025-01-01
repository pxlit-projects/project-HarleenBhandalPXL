import { Routes } from '@angular/router';
import {CreatePostComponent} from "./post/create/create-post/create-post.component";
import {PostComponent} from "./post/post.component";
import {ConceptComponent} from "./post/concept/concept.component";

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
    path: 'concepts',
    component: ConceptComponent
  },
  {
    path: '',
    redirectTo: 'posts',
    pathMatch: 'full'
  },
];
