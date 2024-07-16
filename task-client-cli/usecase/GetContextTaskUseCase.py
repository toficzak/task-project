from cache_wrapper.Data import Data
from usecase.cacher.ContextTaskCacher import ContextTaskCacher


class GetContextTaskUseCase:

    def get_context_task(self) -> Data:
        data = ContextTaskCacher().get_cacher().get_line()
        print(f"Context task id:{data.key}")  # todo: using name might be more readable
        return data
