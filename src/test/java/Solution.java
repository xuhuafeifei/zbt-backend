import java.util.Arrays;

class Solution {
    // dfs(i, j) -> 前i列, 变成j的最小操作数
    // dfs(i, j) = min dfs(i - 1, k) + (m - cnt[i][j])
    // k 属于 [0, 9] 且 k != j
    // int m = grid.length(行), n = grid[0].length(列)
    // cnt[i][j] -> 第i列, 元素j出现的次数
    // memo[i][j] -> 第i列, 变成j的最小操作数
    int m, n;
    int[][] cnt, memo;
    public int minimumOperations(int[][] grid) {
        m = grid.length; n = grid[0].length;
        cnt = new int[n][10];
        memo = new int[n][11];

        // 统计cnt
        for (int i = 0; i < n; ++i) {
            for (int row = 0; row < m; ++row) {
                int j = grid[row][i];
                ++cnt[i][j];
            }
        }
        // 初始化memo
        for (int[] me : memo) Arrays.fill(me, -1);

        int ans = Integer.MAX_VALUE;
        return dfs(n - 1, 10);
    }

    public int dfs(int i, int j) {
        int ans = Integer.MAX_VALUE;
        // 剪枝
        if (memo[i][j] != -1) return memo[i][j];

        if (i == 0) {
            for (int k = 0; k < 10; ++k) {
                ans = Math.min(ans, m - cnt[i][k]);
            }
            memo[i][j] = ans;
            return ans;
        }

        for (int k = 0; k < 10; ++k) {
            if (k == j) continue;
            ans = Math.min(ans, dfs(i - 1, k) + (m - cnt[i][k]));
        }
        memo[i][j] = ans;
        return ans;
    }

    public static void main(String[] args) {
        final Solution solution = new Solution();
        int[][] grid = {{4,4,2,1,2,1,0,4,0,9}};
        System.out.println(solution.minimumOperations(grid));
    }
}
