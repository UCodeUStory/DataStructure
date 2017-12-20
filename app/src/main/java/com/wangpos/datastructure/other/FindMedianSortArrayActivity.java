package com.wangpos.datastructure.other;

import com.wangpos.datastructure.core.BaseActivity;

/**
 * Created by qiyue on 2017/12/19.
 */

public class FindMedianSortArrayActivity extends BaseActivity{


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 > n2) {
            return findMedianSortedArrays(nums2, nums1);
        }
        if (n1 == 0)
            return (n2 % 2 == 0) ? (double) (nums2[n2 / 2] + nums2[(n2 / 2) - 1]) / 2 : nums2[n2 / 2];

        int l = 0, r = n1;

        while (l <= r) {
            int partX = (l + r) / 2,
                    partY = ((n1 + n2 + 1) / 2) - partX;

            int leftX = (partX == 0) ? Integer.MIN_VALUE : nums1[partX - 1];
            int rightX = (partX == n1) ? Integer.MAX_VALUE : nums1[partX];
            int leftY = (partY == 0) ? Integer.MIN_VALUE : nums2[partY - 1];
            int rightY = (partY == n2) ? Integer.MAX_VALUE : nums2[partY];

            if (leftX <= rightY && leftY <= rightX) {
                if ((n1 + n2) % 2 == 0) {
                    int res = Math.max(leftX, leftY);
                    res += Math.min(rightX, rightY);
                    return (double) res / 2;
                } else
                    return Math.max(leftX, leftY);
            } else if (leftX > rightY) {
                r = partX - 1;
            } else
                l = partX + 1;
        }
        return -1;
    }


    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int i = 0;
        int ii = 0;
        int findnume = (nums1.length + nums2.length);
        int findnum = findnume / 2;
        if (findnume % 2 == 0)
        {
            while (i + ii < findnum - 1)
            {
                if (ii >= nums2.length)
                {
                    return (nums1[findnum - ii] + nums1[findnum - ii - 1])/2.0;
                }
                else if (i >= nums1.length)
                {
                    return (nums2[findnum - i] + nums2[findnum - i - 1])/2.0;
                }
                else if (nums1[i] < nums2[ii])
                {
                    i++;
                }
                else
                {
                    ii++;
                }

            }
            int hold;
            if  (ii >= nums2.length)
            {
                hold = nums1[i];
                i++;
            }
            else if (i >= nums1.length)
            {
                hold = nums2[ii];
                ii++;
            }
            else if (nums1[i] < nums2[ii])
            {
                hold = nums1[i];
                i++;
            }
            else
            {
                hold = nums2[ii];
                ii++;
            }
            if (ii >= nums2.length)
            {
                return (hold + nums1[i]) / 2.0;
            }
            else if (i >= nums1.length)
            {
                return (hold + nums2[ii]) / 2.0;
            }
            else if (nums1[i] < nums2[ii])
            {
                return (hold + nums1[i]) / 2.0;
            }
            else
            {
                return (hold + nums2[ii]) / 2.0;
            }
        }
        else
        {
            while (i + ii < findnum)
            {
                if (ii >= nums2.length)
                {
                    return nums1[findnum - ii];
                }
                else if (i >= nums1.length)
                {
                    return nums2[findnum - i];
                }
                else if (nums1[i] < nums2[ii])
                {
                    i++;
                }
                else
                {
                    ii++;
                }
            }
            if  (ii >= nums2.length)
            {
                return nums1[i];
            }
            else if (i >= nums1.length)
            {
                return nums2[ii];
            }
            else if (nums1[i] < nums2[ii])
            {
                return nums1[i];
            }
            else
            {
                return nums2[ii];
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return null;
    }

    @Override
    protected String getTimeData() {
        return null;
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return null;
    }
}
