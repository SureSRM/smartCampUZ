package es.unizar.smartcampuz.model;

import java.io.Serializable;

/**
 * Generic repository for entities.
 * Based on CRUDRepository and used to reverse dependencies.
 */
public interface EntityRepository<T, ID extends Serializable> {

        /**
         * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
         * entity instance completely.
         *
         * @param entity
         * @return the saved entity
         */
        <S extends T> S save(S entity);

        /**
         * Saves all given entities.
         *
         * @param entities
         * @return the saved entities
         * @throws IllegalArgumentException in case the given entity is {@literal null}.
         */
        <S extends T> Iterable<S> save(Iterable<S> entities);

        /**
         * Retrieves an entity by its id.
         *
         * @param id must not be {@literal null}.
         * @return the entity with the given id or {@literal null} if none found
         * @throws IllegalArgumentException if {@code id} is {@literal null}
         */
        T findOne(ID id);

        /**
         * Returns whether an entity with the given id exists.
         *
         * @param id must not be {@literal null}.
         * @return true if an entity with the given id exists, {@literal false} otherwise
         * @throws IllegalArgumentException if {@code id} is {@literal null}
         */
        boolean exists(ID id);

        /**
         * Returns all instances of the type.
         *
         * @return all entities
         */
        Iterable<T> findAll();

        /**
         * Returns all instances of the type with the given IDs.
         *
         * @param ids
         * @return
         */
        Iterable<T> findAll(Iterable<ID> ids);

        /**
         * Returns the number of entities available.
         *
         * @return the number of entities
         */
        long count();

        /**
         * Deletes the entity with the given id.
         *
         * @param id must not be {@literal null}.
         * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
         */
        void delete(ID id);

        /**
         * Deletes a given entity.
         *
         * @param entity
         * @throws IllegalArgumentException in case the given entity is {@literal null}.
         */
        void delete(T entity);

        /**
         * Deletes the given entities.
         *
         * @param entities
         * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
         */
        void delete(Iterable<? extends T> entities);

        /**
         * Deletes all entities managed by the repository.
         */
        void deleteAll();

}
