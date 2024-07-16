from http_wrapper.Requester import Requester
from usecase.GetContextTaskUseCase import GetContextTaskUseCase
from usecase.cacher.TasksCacher import TasksCacher


class AddCommentUseCase:

    def __init__(self, args, requester: Requester):
        self.args = args
        self.requester = requester

    def add_comment(self):
        # todo: generate those checks
        task_id = self.args.task_id
        if task_id is not None:
            task_id = TasksCacher().get_cacher().find_by_key(task_id).value
        else:
            task_data = GetContextTaskUseCase().get_context_task()
            if task_data is None:
                print("Error: no task list id provided, no context task list id")
                return
            task_id = task_data.key

        # todo: this API should be more verbose. Some kind of python contract lib?
        json_data = {
            "content": f"{self.args.comment}"
        }

        print(f"TaskId:{task_id}")
        self.requester.post(
            endpoint=f"/tasks/{task_id}/comments",
            body=json_data
        )
        print(f"Added comment to task {self.args.task_id} [{task_id}]")


if __name__ == "__main__":
    class Args:
        def __init__(self, task_id, comment):
            self.task_id = task_id
            self.comment = comment


    AddCommentUseCase(args=Args(1, "new comment"), requester=Requester("http://localhost:8080")).add_comment()
