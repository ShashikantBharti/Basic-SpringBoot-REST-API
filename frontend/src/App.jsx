import React, { useEffect, useState } from "react";
import { MdEdit, MdDelete } from "react-icons/md";

const App = () => {
  // State variables for managing todos and form inputs
  const [todoId, setTodoId] = useState(null);
  const [todos, setTodos] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const url = "http://localhost:8080/api/todos";

  // Fetch all todos from the backend API
  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await fetch(url);
      const data = await response.json();
      setTodos(data);
    } catch (error) {
      console.error("Error fetching todos:", error);
    }
  };

  // Handle form submission for adding or updating a todo
  const handleSubmit = async (e) => {
    e.preventDefault();
    const todo = { title, description };
    const method = todoId ? "PUT" : "POST";
    const postUrl = todoId ? `${url}/${todoId}` : url;

    try {
      await fetch(postUrl, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
      });
      setTitle("");
      setDescription("");
      setTodoId(null);
      fetchTodos();
    } catch (error) {
      console.error("Error saving todo:", error);
    }
  };

  // Populate form fields when editing a todo
  const handleEdit = (todo) => {
    setTodoId(todo.id);
    setTitle(todo.title);
    setDescription(todo.description);
  };

  // Delete a todo item
  const handleDelete = async (id) => {
    try {
      await fetch(`${url}/${id}`, { method: "DELETE" });
      fetchTodos();
    } catch (error) {
      console.error("Error deleting todo:", error);
    }
  };

  return (
    <div className="flex justify-center items-center mt-4 flex-col gap-4">
      <h1 className="text-3xl font-bold">Simple Todo App: React and Spring Boot</h1>

      {/* Form for adding or updating a todo */}
      <div className="flex flex-col md:flex-row">
        <div className="flex-1 p-10">
          <input
            type="text"
            placeholder="Todo title"
            className="border p-2 w-full rounded"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <textarea
            className="border p-2 w-full mt-3 rounded"
            rows="4"
            placeholder="Todo Description"
            onChange={(e) => setDescription(e.target.value)}
            value={description}
          ></textarea>
          <button
            className="px-4 py-2 mt-2 rounded bg-blue-500 hover:bg-blue-600 transition block text-white"
            onClick={handleSubmit}
          >
            {todoId ? "Update Todo" : "Add Todo"}
          </button>
        </div>

        {/* Display list of todos */}
        <div className="flex-1 px-10">
          <h2 className="text-2xl font-bold mb-3">Todos:</h2>
          {todos.map((todo) => (
            <div
              className="flex justify-between items-center my-2 py-2 rounded px-5 bg-gray-800 text-white"
              key={todo.id}
            >
              <div>
                <h3 className="font-bold text-xl">{todo.title}</h3>
                <p>{todo.description}</p>
              </div>
              <div className="flex gap-2">
                <button
                  className="p-2 bg-blue-500 rounded cursor-pointer"
                  title="Edit"
                  onClick={() => handleEdit(todo)}
                >
                  <MdEdit />
                </button>
                <button
                  className="p-2 bg-red-500 rounded cursor-pointer"
                  title="Delete"
                  onClick={() => handleDelete(todo.id)}
                >
                  <MdDelete />
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default App;
