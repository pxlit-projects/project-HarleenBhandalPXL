import { TestBed } from '@angular/core/testing';

import { PostService } from './post.service';
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {Post} from "../../models/post.model";
import {provideHttpClient} from "@angular/common/http";

describe('PostService', () => {
  let service: PostService;
  let httpTestingController: HttpTestingController;

  const mockPosts: Post[] = [
    new Post(1, 'Post 1', 'Content 1', 'Author 1', "2024-01-01"),
    new Post(2, 'Post 2', 'Content 2', 'Author 2', "2024-01-02"),
  ]

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PostService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(PostService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get published posts', () => {
    service.getPublishedPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(service.api);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get concept posts', () => {
    service.getConceptPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${service.api}/concepts`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get pending posts', () => {
    service.getPendingPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${service.api}/pending`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should get rejected posts', () => {
    service.getRejectedPosts().subscribe(posts => {
      expect(posts).toEqual(mockPosts);
    });

    const req = httpTestingController.expectOne(`${service.api}/rejected`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should create a post', () => {
    const newPost = new Post(3, 'Post 3', 'Content 3', 'Author 3', '2024-01-03');
    service.createPost(newPost).subscribe(post => {
      expect(post).toEqual(newPost);
    });

    const req = httpTestingController.expectOne(service.api);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Role')).toBe('editor');
    req.flush(newPost);
  });

  it('should save a post as concept', () => {
    const newPost = new Post(3, 'Post 3', 'Content 3', 'Author 3', '2024-01-03');
    service.savePostAsConcept(newPost).subscribe(post => {
      expect(post).toEqual(newPost);
    });

    const req = httpTestingController.expectOne(`${service.api}/concepts/add`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Role')).toBe('editor');
    req.flush(newPost);
  });

  it('should edit a post', () => {
    const updatedPost = new Post(1, 'Updated Post 1', 'Updated Content 1', 'Updated Author 1', '2024-01-01');
    service.editPost(1, updatedPost).subscribe(post => {
      expect(post).toEqual(updatedPost);
    });

    const req = httpTestingController.expectOne(`${service.api}/1/edit`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Role')).toBe('editor');
    req.flush(updatedPost);
  });

  it('should get a post by id', () => {
    const postId = 1;
    service.getPost(postId).subscribe(post => {
      expect(post).toEqual(mockPosts[0]);
    });

    const req = httpTestingController.expectOne(`${service.api}/${postId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts[0]);
  });

  it('should update a post to pending', () => {
    const updatedPost = new Post(1, 'Updated Post 1', 'Updated Content 1', 'Updated Author 1', '2024-01-01');
    service.updateToPending(1).subscribe(post => {
      expect(post).toEqual(updatedPost);
    });

    const req = httpTestingController.expectOne(`${service.api}/1/pending`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Role')).toBe('editor');
    req.flush(updatedPost);
  });
});
