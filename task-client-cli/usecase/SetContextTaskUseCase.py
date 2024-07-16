from cache_wrapper.Data import Data
from usecase.cacher.ContextTaskCacher import ContextTaskCacher
from usecase.cacher.TasksCacher import TasksCacher


class SetContextTaskUseCase:

    def __init__(self, args):
        self.args = args

    def set_context_task(self):
        task_id = self.args.alias_task_id
        cached_task = TasksCacher().get_cacher().find_by_key(task_id)

        if cached_task is None:
            print(f"No task associated with id: {task_id}")

        ContextTaskCacher().get_cacher().clear_file_then_persist(Data(key=cached_task.value, name=cached_task.name))
