package com.ryansusana.elepy.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryansusana.elepy.dao.Crud;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class DeleteImpl<T> implements Delete<T> {

    @Override
    public Optional<T> delete(Request request, Response response, Crud<T> dao, ObjectMapper objectMapper) {

        final Optional<T> id = dao.getById(request.params("id"));
        if (id.isPresent()) {
            dao.delete(request.params("id"));
        }
        return id;
    }
}
