package io.mservice.todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TodoService implements ITodoService {

	private final EntityManager entityManager;

	public TodoService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Todo> findAll() {
		return entityManager.createNamedQuery("Todo.findAll", Todo.class).getResultList();
	}

	@Override
	public Todo findById(UUID id) {
		return entityManager.find(Todo.class, id);
	}

	@Override
	@Transactional
	public Todo save(Todo resource) {
		entityManager.persist(resource);
		return resource;
	}

	@Override
	@Transactional
	public Todo update(Todo resource) {
		return entityManager.merge(resource);
	}

	@Override
	@Transactional
	public boolean deleteById(UUID id) {
		Optional<Todo> todo = Optional.ofNullable(findById(id));
		todo.ifPresent(entityManager::remove);
		return todo.isPresent();
	}

}
