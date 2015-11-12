/*
 * Copyright (c) 2015. <Chungseok Baek>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.image.send.camera;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.image.send.R;
import com.image.send.mainboard.model.ImageContent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


/**
 * Camera Fragment
 */
public class CameraFragment extends Fragment {
    private static final String TAG = "CameraFragment";
    private static String TMP_FILENAME = "";
    private Camera mCamera;
    private AutoFitSurfaceView mSurfaceView;
    private Button mTakeButton;
    private Button mNextButton;
    private OnFragmentInteractionListener mListener;
    private ImageView mImageView;
    private Button mRetakeButton;
    private ProgressDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_camera, parent, false);

        mTakeButton = (Button) view.findViewById(R.id.button_action_take);
        mNextButton = (Button) view.findViewById(R.id.button_action_next);
        mRetakeButton = (Button) view.findViewById(R.id.button_action_retake);
        mImageView = (ImageView) view.findViewById(R.id.view_after_take);

        mImageView.setEnabled(false);
        mImageView.setVisibility(View.INVISIBLE);
        mNextButton.setVisibility(View.INVISIBLE);
        mRetakeButton.setVisibility(View.INVISIBLE);

        mTakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                        Log.d(TAG, "shutter clicked");
                    }
                }, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                        String filename = UUID.randomUUID().toString() + ".jpg";
                        FileOutputStream os = null;
                        boolean success = true;
                        try {
                            os = getActivity().openFileOutput(filename, Context.MODE_APPEND);
                            os.write(data);
                        } catch (Exception e) {
                            Log.e(TAG, "Error writing to file " + filename, e);
                            success = false;
                        } finally {
                            try {
                                if (os != null) {
                                    os.close();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Error closing file", e);
                                success = false;
                            }
                        }

                        final DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
                        final int w = dm.widthPixels;
                        final int h = dm.heightPixels;

                        if (success) {
                            TMP_FILENAME = filename;

                            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                                    .build();
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            if(!imageLoader.isInited())
                                imageLoader.init(config);

                            imageLoader.displayImage("file:///data/data/com.image.send/files/" + filename, mImageView,
                                    new DisplayImageOptions.Builder()
                                            .resetViewBeforeLoading(true)
                                            .postProcessor(new BitmapProcessor() {
                                                @Override
                                                public Bitmap process(Bitmap bitmap) {
                                                    Matrix m = new Matrix();
                                                    m.setRotate(90);
                                                    Bitmap bitmapRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                                                    return Bitmap.createScaledBitmap(bitmapRotated, w, h, false);
                                                }
                                            })
                                            .build());
                            mImageView.setVisibility(View.VISIBLE);
                            mImageView.setEnabled(true);
                            mRetakeButton.setVisibility(Button.VISIBLE);
                            mNextButton.setVisibility(Button.VISIBLE);
                            Log.i(TAG, "JPEG saved at" + filename);
                        }
                    }
                });
            }
        });

        // retake picture and delete the saved jpeg file from local data.
        mRetakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetakeButton.setVisibility(Button.INVISIBLE);
                mNextButton.setVisibility(Button.INVISIBLE);
                mImageView.setVisibility(View.INVISIBLE);

                File file_to_delete = getActivity().getFileStreamPath(TMP_FILENAME);


                if (file_to_delete != null) {
                    boolean delete_result = file_to_delete.delete();
                    Log.d(TAG, "file deleted:" + delete_result);
                    TMP_FILENAME = "";
                }
                mCamera.startPreview();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageContent.Image image = new ImageContent.Image();
                String filename = image.getId()+".jpg";
                File to = getActivity().getFileStreamPath(filename);
                File file_to_rename = getActivity().getFileStreamPath(TMP_FILENAME);
                boolean rename_result = file_to_rename.renameTo(to);

                if(rename_result){
                    Log.d(TAG,"successfully save tmp_img to ImageContent");
                    TMP_FILENAME = "";
                    mListener.setCurrentItem(2);
                    image.setName(filename);
                    ImageContent.addItem(image);
                }
            }
        });

        if (mImageView.isEnabled()) {
            mTakeButton.setVisibility(Button.INVISIBLE);
        }

        mSurfaceView = (AutoFitSurfaceView) view.findViewById(R.id.camera_autofit_surface_view);
        SurfaceHolder holder = mSurfaceView.getHolder();

        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder holder) {
                // tell the camera to use this surface as its preview area
                try {
                    if (mCamera != null) {
                        Log.d(TAG, "setPreview");
                        mTakeButton.setVisibility(View.VISIBLE);
                        mCamera.setPreviewDisplay(holder);
                    } else {
                        Log.d(TAG, "Camera is null1");
                    }
                } catch (IOException exception) {
                    Log.e(TAG, "Error setting up preview display", exception);
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // we can no longer display on this surface, so stop the preview.
                if (mCamera != null) {
                    mTakeButton.setVisibility(View.INVISIBLE);
                    mCamera.stopPreview();
                } else {
                    Log.d(TAG, "Camera is null2");
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (mCamera == null) return;


                // the surface has changed size; update the camera preview size
                Camera.Parameters parameters = mCamera.getParameters();
                final DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
                w = dm.widthPixels;
                h = dm.heightPixels;

                // crop remaining with or height so that it can match to the screen size.
                parameters.setPictureSize(h, w);

                mSurfaceView.setAspectRatio(parameters.getPreviewSize().height, parameters.getPreviewSize().width);
                parameters.setPreviewSize(h, w);
                mCamera.setDisplayOrientation(90);

                mCamera.setParameters(parameters);
                try {
                    mCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Could not start preview", e);
                    mCamera.release();
                    mCamera = null;
                }
            }
        });
        return view;
    }

    /**
     * life cycle for Camera Instance
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onnResume called");
        if (mCamera == null) {
            mCamera = Camera.open(0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * life cycle for Camera Instance
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPauseCalled");
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        if (!TMP_FILENAME.equals("")) {
            File file_to_delete = getActivity().getFileStreamPath(TMP_FILENAME);
            boolean delete_result = file_to_delete.delete();
            Log.d(TAG, "file deleted:" + delete_result);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void setCurrentItem(int pos);
    }
}