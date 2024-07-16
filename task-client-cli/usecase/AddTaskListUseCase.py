from http_wrapper.Requester import Requester


class AddTaskListUseCase:

    def __init__(self, args, requester: Requester):
        self.args = args
        self.requester = requester

    def add_task_list(self):
        print(f"Added task list: {self.args.task_list_name}")

        json_data = {
            "name": f"{self.args.task_list_name}"
        }

        self.requester.post(
            endpoint="/lists",
            body=json_data
        )


if __name__ == "__main__":
    class Args:
        def __init__(self, task_list_name):
            self.task_list_name = task_list_name


    AddTaskListUseCase(Args("test_task_list_name"), Requester()).add_task_list()
