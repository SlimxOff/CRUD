package ru.netology.repository;

import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  public List<Post> all() {
    return posts.values().stream().collect(Collectors.toList());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.getAndIncrement());
      posts.put(post.getId(), post);
    } else {
      if (posts.containsKey(post.getId())) {
        posts.put(post.getId(), post);
      } else {
        throw new IllegalArgumentException("Post with id " + post.getId() + " not found");
      }
    }
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}