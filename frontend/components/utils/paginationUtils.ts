export function calculatePagination(
    length: number,
    offset: number,
    total: number
  ): { start: number; end: number; total: number } {
    let start;
    let end;
    if (total === 0) {
      total = length;
    }
    if (total < offset) {
      start = 1;
      end = offset;
    } else {
      if (offset === 0) {
        if (total === length) {
          start = 1;
          end = total;
        } else {
          start = total - length;
          end = total;
        }
      } else {
        start = offset - length + 1;
        end = offset;
      }
    }
  
    return { start, end, total };
  }